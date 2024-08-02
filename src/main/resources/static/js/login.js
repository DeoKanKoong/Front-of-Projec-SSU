document.addEventListener('DOMContentLoaded', function() {
    document.querySelector('.login-button').addEventListener('click', function() {
        validateForm();
    });
});

function redirectToJoin() {
    window.location.href = '/join';
}

function redirectToForgottenId() {
    window.location.href = '/ForgottenId';
}

function redirectToForgottenPw() {
    window.location.href = '/ForgottenPw';
}

function validateForm() {
    const username = document.querySelector('.input-box[type="text"]').value;
    const password = document.querySelector('.input-box[type="password"]').value;
    const usernameError = document.getElementById('usernameError');
    const passwordError = document.getElementById('passwordError');

    // 초기화
    usernameError.textContent = '';
    passwordError.textContent = '';

    let valid = true;

    if (!username) {
        usernameError.textContent = '아이디를 입력해주세요.';
        valid = false;
    }

    if (!password) {
        passwordError.textContent = '비밀번호를 입력해주세요.';
        valid = false;
    }

    if (valid) {
        // 서버로 로그인 요청 보내기
        fetch('/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username: username, password: password })
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                window.location.href = '/MainCommunity.html';
            } else {
                if (data.error === 'invalid_username') {
                    usernameError.textContent = '아이디가 틀렸습니다.';
                } else if (data.error === 'invalid_password') {
                    passwordError.textContent = '비밀번호가 틀렸습니다.';
                } else {
                    alert('알 수 없는 오류가 발생했습니다.');
                }
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('서버와의 통신 중 오류가 발생했습니다.');
        });
    }
}
