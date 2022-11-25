var config = {
  type: 'line',
  data: {
    datasets: []
  },
  options: {
    legend: {
      display: false
    },
    responsive: true,
    title: {
      display: false,
    },
    scales: {
      xAxes: [{
        type: "time"
      }],
      yAxes: []
    }
  }
};

window.onload = function () {
  var ctx = document.getElementById("canvas").getContext("2d");
  new Chart(ctx, config);
};