<!DOCTYPE html>
<html lang="en">
<head>
    <#include "head-meta.ftlh">
  <script src="js/moment.js"></script>
  <script src="js/jquery.js"></script>
  <script src="js/chart.js"></script>
  <script src="js/jmhchart.js"></script>
  <script type="text/javascript">
    <#outputformat "JSON">
    config.data.datasets = [${dataset}, ${minDataset}, ${maxDataset}];
    config.options.scales.yAxes = [{
      scaleLabel: {
        display: true,
        labelString: '${benchmark.unit()}'
      }
    }]
    </#outputformat>
  </script>
</head>
<body>
<#include "header.ftlh">
<main>
  <figure>
    <canvas id="canvas"></canvas>
    <figcaption>${benchmark.name()}</figcaption>
  </figure>
</main>
</body>
</html>