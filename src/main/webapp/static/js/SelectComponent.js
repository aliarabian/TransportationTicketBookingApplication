import fetchService from "./service/CityFetchService.js";
export { SelectComponents as default };
var SelectComponents = (function () {
  var SelectComponent = {
    componentElement: null,
    citiesList: null,
    input: null,
    init(componentElement) {
      this.componentElement = componentElement;
      this.citiesList = this.componentElement.querySelector(".cities");
      this.input = this.componentElement.querySelector("input");
      this.bindUIActions();
    },
    bindUIActions() {
      let hiddenBackground = document.getElementById("hidden-background");
      //  Set list item value to input value
      this.citiesList.addEventListener("click", function (evt) {
        if (evt.target.tagName.toLowerCase() === "li") {
          this.previousElementSibling.value = evt.target.innerText;
        }
      });

      // show cities list when clicked on select component
      this.componentElement.addEventListener("click", () => {
        if (
          this.citiesList.style.display === "" ||
          this.citiesList.style.display === "none"
        ) {
          hiddenBackground.style.display = "block";
          this.citiesList.style.display = "block";
        } else {
          hiddenBackground.style.display = "none";
          this.citiesList.style.display = "none";
        }
        hiddenBackground.addEventListener("click", () => {
          this.citiesList.style.display = "none";
          hiddenBackground.style = "none";
        });
      });
    },
    renderCitiesList(citiesData) {
      citiesData.forEach((cityData) => {
        let city = document.createElement("li");
        city.appendChild(document.createTextNode(cityData));
        this.citiesList.appendChild(city);
      });
    },
  };
  return {
    init: function () {
      var selectComponents = [];
      document
        .querySelectorAll(".select-component")
        .forEach((componentElement) => {
          let component = Object.create(SelectComponent);
          component.init(componentElement);
          selectComponents.push(component);
        });

      fetchService.fetch().then((respone) => {
        selectComponents.forEach((component) =>
          component.renderCitiesList(respone)
        );
      });
    },
  };
})();
