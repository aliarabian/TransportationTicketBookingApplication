import FlightDetailsModal from "./FlightDetailsModal.js";
import FlightExpandableDetails from "./FlightExpandableDetailsComponent.js";

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

            let expandHandler = {};
            Object.assign(expandHandler, FlightExpandableDetails);
            flightDetailsExpandComponent.addEventListener("click", function (evt) {
                evt.stopPropagation();
                this.innerText = expandHandler.toggle(
                    flightCard.querySelector(".flight-card-body .expandable")
                );
            });
        });
    },
    toggle(section) {
        this.flights.forEach((flightCard) => {
            if (
                flightCard.getAttribute("data-section")
                    .split(" ")[0]
                    .toLowerCase() === section
            ) {
                flightCard.parentElement.classList.toggle("deactive");
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
