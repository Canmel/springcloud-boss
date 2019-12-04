try {
    new Vue();
} catch(e) {
    location.href="/survey/";
    sessionStorage.setItem('currentPage', location.pathname);
}