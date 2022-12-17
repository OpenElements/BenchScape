<!DOCTYPE html>
<html lang="en">
<head>
    <#include "../head-meta.ftlh">
  <script src="/js/moment.js"></script>
  <script src="/js/jquery.js"></script>
  <script src="/js/chart.js"></script>
  <script src="/js/jmhchart.js"></script>
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
<#include "../header.ftlh">
<main class="container mx-auto p-12">
  <h2 class="text-center p-1 pt-3 font-sans text-lg antialiased font-semibold">Timeseries graph for
    JMH
    test ${benchmark.name()}</h2>
  <figure class="rounded border-2 border-slate-400 border-solid p-2">
    <canvas id="canvas"></canvas>
  </figure>
</main>
</body>
</html>