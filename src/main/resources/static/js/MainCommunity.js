document.addEventListener('DOMContentLoaded', function() {
    const username = sessionStorage.getItem('username');
    const department = sessionStorage.getItem('department');
    const profilePic = sessionStorage.getItem('profilePic');

    if (!username || !department || !profilePic) {
        window.location.href = 'login.html'; // 세션 정보가 없으면 로그인 페이지로 이동
        return;
    }

    // 사용자 정보 표시
    document.getElementById('user-name').textContent = `이름: ${username}`;
    document.getElementById('user-department').textContent = `학과(부): ${department}`;
    document.getElementById('profile-picture').src = profilePic;

    // 팀 모집 게시글의 최근 게시글들을 가져와서 표시 (최대 6개)
    fetch('http://localhost:8080/posts')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(posts => {
            const boardItemsContainer = document.getElementById('board-items');
            boardItemsContainer.innerHTML = ''; // 기존 내용 지우기
            posts.slice(0, 6).forEach(post => {
                const boardItem = document.createElement('div');
                boardItem.className = 'board-item';
                boardItem.innerHTML = `
                    <img src="${post.teamLogo}" alt="Team Logo" class="team-logo">
                    <div class="board-item-content">
                        <h2 class="board-item-title">${post.title.length > 15 ? post.title.slice(0, 15) + '...' : post.title}</h2>
                        <p class="board-item-desc">${post.author}(${post.year}) ${post.department}</p>
                        <div class="board-item-meta">
                            <div class="detail-item">
                                <span class="detail-label">분야</span>
                                <span class="detail-value">${post.field}</span>
                            </div>
                            <div class="detail-item">
                                <span class="detail-label">조건</span>
                                <span class="detail-value">${post.condition}</span>
                            </div>
                            <div class="detail-item">
                                <span class="detail-label">우대사항</span>
                                <span class="detail-value">${post.preferential}</span>
                            </div>
                        </div>
                        <div class="board-item-footer">
                            <span class="current-members">현재 인원 (${post.currentMembers}명 / ${post.maxMembers}명)</span>
                            <span class="deadline">~${post.deadline}</span>
                        </div>
                    </div>
                `;
                boardItemsContainer.appendChild(boardItem);
            });
        })
        .catch(error => console.error('Error fetching recent posts:', error));

    // 크롤링한 프로그램 데이터 읽어서 표시 (최대 3개)
    fetch('../static/js/programs.json')
        .then(response => response.json())
        .then(programs => {
            const programItemsContainer = document.getElementById('program-items');
            programItemsContainer.innerHTML = ''; // 기존 내용 지우기
            programs.slice(0, 3).forEach(program => {
                const truncatedTitle = program.title.length > 20 ? program.title.slice(0, 20) + '...' : program.title;
                const programItem = document.createElement('div');
                programItem.className = 'program-item';
                programItem.innerHTML = `
                    <div class="program-item-title">${truncatedTitle}</div>
                    <div class="program-item-desc">${program.dateRange}</div>
                    <div class="program-item-meta">${program.type} & ${program.progress}</div>
                `;
                programItemsContainer.appendChild(programItem);
            });
        })
        .catch(error => console.error('Error fetching programs:', error));
});

function logout() {
    sessionStorage.clear();
    window.location.href = 'login.html';
}

function redirectTo(page) {
    window.location.href = page;
}
