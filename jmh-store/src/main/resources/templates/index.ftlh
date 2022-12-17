<!DOCTYPE html>
<html lang="en">
<head>
    <#include "head-meta.ftlh">
</head>
<body>
<#include "header.ftlh">

<main class="container mx-auto py-12">
  <p class="font-sans">The following JMH Benchmarks are available:</p>

  <table class="w-full text-sm text-left">
    <thead class="bg-gray-50">
    <tr>
      <th class="py-3 px-6">ID</th>
      <th class="py-3 px-6">Name</th>
      <th class="py-3 px-6">Unit</th>
      <th class="py-3 px-6"><span class="sr-only">Show</span></th>
    </tr>
    </thead>
    <tbody>
    <#list benchmarks as benchmark>
      <tr>
        <td class="py-4 px-6">${benchmark.id()?string.computer}</td>
        <td class="py-4 px-6">${benchmark.name()}</td>
        <td class="py-4 px-6">${benchmark.unit()}</td>
        <td class="py-4 px-6"><a
              class="font-medium text-blue-600 dark:text-blue-500 hover:underline"
              href="timeseries/graph?id=${benchmark.id()?string.computer}">Graph</a> <a
              class="font-medium text-blue-600 dark:text-blue-500 hover:underline"
              href="timeseries/table?id=${benchmark.id()?string.computer}">Table</a></td>
      </tr>
    </#list>
    </tbody>
  </table>
</main>
</body>
</html>