var FlightExpandableDetails = {
  expanded: false,
  toggle: function (expandable) {
    expandable.classList.toggle("collapse");
    if (this.expanded) {
      this.expanded = false;
      expandable.style.maxHeight = null;
      return "more";
    }
    expandable.style.maxHeight = expandable.scrollHeight + "px";
    this.expanded = true;
    return "less";
  },
};

export default FlightExpandableDetails;
