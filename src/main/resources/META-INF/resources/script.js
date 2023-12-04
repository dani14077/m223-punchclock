const URL = 'http://localhost:8080';
let bookings = [];
let loggedInUser = null;

const dateAndTimeToDate = (dateString, timeString) => {
    return new Date(`${dateString}T${timeString}`).toISOString();
};

const createBooking = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const booking = {};
    booking['bookingDate'] = dateAndTimeToDate(formData.get('bookingDate'), formData.get('bookingTime'));

    fetch(`${URL}/bookings`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
        },
        body: JSON.stringify(booking)
    }).then((result) => {
        result.json().then((booking) => {
            bookings.push(booking);
            renderBookings();
        });
    });
};

const indexBookings = () => {
    fetch(`${URL}/bookings`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
        }
    }).then((result) => {
        result.json().then((result) => {
            bookings = result;
            renderBookings();
        });
    });
    renderBookings();
};

const renderBookings = () => {
    const display = document.querySelector('#bookingDisplay');
    display.innerHTML = '';
    bookings.forEach((booking) => {
        const row = document.createElement('tr');
        row.appendChild(createCell(booking.id));
        row.appendChild(createCell(new Date(booking.bookingDate).toLocaleString()));
        display.appendChild(row);
    });
};

const createCell = (text) => {
    const cell = document.createElement('td');
    cell.innerText = text;
    return cell;
};

document.addEventListener('DOMContentLoaded', function(){
    const registerForm = document.querySelector('#registerForm');
    const loginForm = document.querySelector('#loginForm');
    const createBookingForm = document.querySelector('#createBookingForm');
    const bookingContainer = document.querySelector('#booking-container');
    const registerContainer = document.querySelector('#register-container');

    loginForm.addEventListener('submit', function (e) {
        e.preventDefault();
        const formData = new FormData(e.target);
        const credential = {
            email: formData.get('email'),
            password: formData.get('password')
        };

        fetch(`${URL}/users/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(credential)
        }).then((response) => {
            if (response.ok) {
                response.json().then((user) => {
                    // Store the JWT token in local storage
                    localStorage.setItem('jwtToken', user.token);

                    // Update the logged-in user
                    loggedInUser = user;

                    // Hide the login and registration forms
                    loginForm.style.display = 'none';
                    registerContainer.style.display = 'none';

                    // Show the booking form and bookings
                    bookingContainer.style.display = 'block';
                    indexBookings();
                });
            }
        });
    });

    registerForm.addEventListener('submit', function (e) {
        e.preventDefault();
        const formData = new FormData(e.target);
        const user = {
            firstName: formData.get('firstName'),
            lastName: formData.get('lastName'),
            email: formData.get('email'),
            password: formData.get('password')
        };

        fetch(`${URL}/users`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        }).then((response) => {
            if (response.ok) {
                // Automatically log in the newly registered user
                loginForm.querySelector('#email').value = user.email;
                loginForm.querySelector('#password').value = user.password;
                loginForm.submit();
            }
        });
    });

    createBookingForm.addEventListener('submit', createBooking);

    indexBookings();
});
