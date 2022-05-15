var FlightDetailsModal = {
    template: (
        flightNO,
        sectionClass,
        departureTime,
        offset,
        destination,
        planeModel,
        availableSeats,
        privileges
    ) => `
    <article id="flight-details-modal">
    <div id="flight-details-modal-content">
        <div class="modal-header">
          <p>FlightNO: <span class="modal-flight-no">${flightNO}</span></p>
          <p>${capitalize(sectionClass)} Class</p>
          <p>Departure Time: <span class="modal-flight-datetime">${departureTime}</span></p>
        </div>
        <div class="modal-body">
          <p>${offset} to ${destination}</p>
          <p>Plane: ${planeModel}</p>
          <p>Available Seats: ${availableSeats}</p>
          <h5>Privileges:</h5>
          <ul class="section-privileges">
            ${createPrivilegesHtmlListItems(privileges)}
          </ul>
        </div>
        <div class="modal-footer">
      </div>
    </article>
    `,
    modal: null,
    modalHtmlTemplate: null,
    init() {
        this.modalHtmlTemplate = document.createElement("template");
    },
    show(flightCard) {
        this.modalHtmlTemplate.innerHTML = this.template(
            flightCard.getAttribute("data-flight-no"),
            flightCard.getAttribute("data-section"),
            flightCard.getAttribute("data-departure-datetime"),
            flightCard.getAttribute("data-flight-offset"),
            flightCard.getAttribute("data-flight-destination"),
            flightCard.getAttribute("data-flight-plane-model"),
            flightCard.getAttribute("data-flight-available-seats"),
            flightCard.getAttribute("data-flight-section-privileges")
        );
        this.modal = this.modalHtmlTemplate.content.firstElementChild;
        document.body.appendChild(this.modal);
        this.modal.addEventListener("click", (evt) => {
            if (evt.target !== this.content) {
                this.close();
            }
        });
    },
    close() {
        document.body.removeChild(this.modal);
    },
};

function createPrivilegesHtmlListItems(privileges) {
    let privilegesListItems = "";
    console.log(privileges)
    let lastIndexOfComma = privileges.lastIndexOf(",");
    if (lastIndexOfComma > -1) {
        privileges = privileges.slice(0, lastIndexOfComma) + privileges.slice(lastIndexOfComma + 1);
    }
    let parsedPrivileges = JSON.parse(privileges);
    parsedPrivileges.privileges.forEach((privilege) => {
        privilegesListItems += `<li data-privilege-id="${privilege.id}">${privilege.description}</li>`;
    });
    return privilegesListItems.trim().length > 0 ? privilegesListItems.trim() : "Nothing.";
}

function capitalize(str) {
    return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
}

export default FlightDetailsModal;
