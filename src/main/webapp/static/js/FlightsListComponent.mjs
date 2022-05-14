import FlightDetailsModal from "./FlightDetailsModal.mjs";

var FlightsList = {
    flights: document.querySelectorAll(".flight-card"),
    flightsList: document.querySelector("#flights-container"),
    bindModal() {
        FlightDetailsModal.init();
        this.flights.forEach((flightCard) => {
            flightCard.addEventListener("click", function () {
                FlightDetailsModal.show(flightCard);
            });
            let flightDetailsExpandComponent = flightCard.querySelector(
                ".flight-details-see-more"
            );

            flightDetailsExpandComponent.addEventListener("click", function (evt) {
                evt.stopPropagation();
            });
        });
    },
    toggle(section) {
        this.flights.forEach((flightCard) => {
            if (flightCard.getAttribute("data-section").split(" ")[0].toLowerCase() == section) {
                flightCard.classList.toggle("deactive");
            }
        });
    },
};
var FilterableFlightsList = Object.create(FlightsList);
Object.defineProperty(FilterableFlightsList, "checkboxes", {
    value: [],
    writable: true,
});
FilterableFlightsList.init = function init() {
    this.checkboxes = document.querySelectorAll("#filter-component input");
    this.bindModal();
    this.checkboxes.forEach((checkbox) => {
        checkbox.addEventListener("change", (evt) => {
            this.toggle(evt.target.value);
        });
    });
};

export default FilterableFlightsList;
