export { fetchService as default };

var fetchService = (function () {
  var citites = ["Tehran", "Shiraaz", "London", "Paris", "Istanbul"];

  return {
    fetch: function fetchCities() {
      return Promise.resolve(citites);
    },
  };
})();
