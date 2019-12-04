try {
    new Vue();
} catch(e) {
    location.href="/attendance/";
    sessionStorage.setItem('currentPage', location.pathname);
}