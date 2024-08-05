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

    // 팀 모집 게시글의 최근 게시글들을 가져와서 표시
    fetch('http://localhost:5000/posts')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(posts => {
            const boardItemsContainer = document.getElementById('board-items');
            boardItemsContainer.innerHTML = ''; // 기존 내용 지우기
            posts.forEach(post => {
                const boardItem = document.createElement('div');
                boardItem.className = 'board-item';
                boardItem.innerHTML = `
                    <img src="${post.teamLogo}" alt="Team Logo" class="team-logo">
                    <div class="board-item-content">
                        <h2 class="board-item-title">${post.title}</h2>
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
});