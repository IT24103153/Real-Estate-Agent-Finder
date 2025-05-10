function validateForm() {
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const phone = document.getElementById('phone').value;
    const location = document.getElementById('location').value;
    const experience = document.getElementById('experience').value;
    const specialties = document.getElementById('specialties').value;
    const rating = document.getElementById('rating').value;

    if (!name || !email || !phone || !location || !experience || !specialties || !rating) {
        alert('Please fill in all fields.');
        return false;
    }

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
        alert('Please enter a valid email address.');
        return false;
    }

    const phonePattern = /^\d{10}$/;
    if (!phonePattern.test(phone)) {
        alert('Please enter a valid 10-digit phone number.');
        return false;
    }

    if (rating < 0 || rating > 5) {
        alert('Rating must be between 0 and 5.');
        return false;
    }

    return true;
}