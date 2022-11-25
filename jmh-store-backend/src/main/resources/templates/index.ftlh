<!DOCTYPE html>
<html lang="en">
<head>
    <#include "head-meta.ftlh">
</head>
<body>
<#include "header.ftlh">
<main>
  <p>The following JMH Benchmarks are available:</p>
  <ul>
      <#list benchmarks as benchmark>
        <li><a href="graph?id=${benchmark.id()?string.computer}">${benchmark.name()}</a></li>
      </#list>
  </ul>
</main>
</body>
</html>