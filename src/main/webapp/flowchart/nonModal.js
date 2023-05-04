const openPopupBtn = document.getElementById("open-popup");
const closePopupBtn = document.getElementById("close-popup");
const popupContainer = document.getElementById("popup-container");

openPopupBtn.addEventListener("click", function() {
  popupContainer.style.display = "block";
});

closePopupBtn.addEventListener("click", function() {
  popupContainer.style.display = "none";
});

popupContainer.addEventListener("click", function(event) {
  if (event.target === popupContainer) {
    //popupContainer.style.display = "none";
  }
});
