document.addEventListener('DOMContentLoaded', function() {
    document.querySelector('.login-button').addEventListener('click', login);
});

function redirectToJoin() {
    window.location.href = 'join.html';
}

function redirectToForgottenId() {
    window.location.href = 'forgottenId.html';
}

function redirectToForgottenPw() {
    window.location.href = 'forgottenPw.html';
}

function login() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
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
        // 로그인 요청
        fetch('http://localhost:8080/loginProc', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('로그인 실패');
            }
        })
        .then(data => {
            // 백엔드에서 유저 정보 받아와서 세션에 저장
            sessionStorage.setItem('username', data.name);
            sessionStorage.setItem('department', data.department);
            sessionStorage.setItem('profilePic', data.profilePic);
            sessionStorage.setItem('token', data.token); // 토큰 저장

            // 로그인 성공 시 메인 커뮤니티 페이지로 이동
            window.location.href = 'MainCommunity.html';
        })
        .catch(error => {
            usernameError.textContent = '아이디 또는 비밀번호가 잘못되었습니다.';
            console.error('로그인 오류:', error);
        });
    }
}

