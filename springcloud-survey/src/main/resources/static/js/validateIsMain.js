try {
    new Vue();
} catch(e) {
    location.href="/survey/";
    sessionStorage.setItem('currentPage', location.pathname);
}

// try {
//     leaveRoom();
// }catch (e) {
//     console.error('-=-=-=-=-=-=---',e)
// }