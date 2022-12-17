<!DOCTYPE html>
<html lang="en">
<head>
    <#include "../head-meta.ftlh">
</head>
<body>
<#include "../header.ftlh">


<main class="container mx-auto py-12">
  <p class="font-sans">All ${dataPoints?size} data points for the timeseries of
    the ${benchmarkName}
    benchmark:</p>
  <table class="w-full text-sm text-left h-full">
    <thead class="flex flex-col bg-gray-50 w-full">
    <tr class="flex w-full">
      <th class="py-3 px-6 w-2/12">Date & Time</th>
      <th class="py-3 px-6 w-1/12">Value (${unit})</th>
      <th class="py-3 px-6 w-1/12">Error (${unit})</th>
      <th class="py-3 px-6 w-1/12">MinValue (${unit})</th>
      <th class="py-3 px-6 w-1/12">MaxValue (${unit})</th>
      <th class="py-3 px-6 w-1/12">Processor Count</th>
      <th class="py-3 px-6 w-1/12">Memory Size</th>
      <th class="py-3 px-6 w-4/12">JVM Version</th>
    </tr>
    </thead>
    <tbody
        class="flex flex-col overflow-y-scroll w-full text-sm text-left"
        style="height: 50vh;">
    <#list dataPoints as dataPoint>
      <tr class="flex w-full">
        <td class="py-4 px-6 w-2/12">${dataPoint.dateAndTime()}</td>
        <td class="py-4 px-6 w-1/12">${dataPoint.value()}</td>
        <td class="py-4 px-6 w-1/12">${dataPoint.error()}</td>
        <td class="py-4 px-6 w-1/12">${dataPoint.min()}</td>
        <td class="py-4 px-6 w-1/12">${dataPoint.max()}</td>
        <td class="py-4 px-6 w-1/12">${dataPoint.processorCount()}</td>
        <td class="py-4 px-6 w-1/12">${dataPoint.memorySize()}</td>
        <td class="py-4 px-6 w-4/12">${dataPoint.jvmVersion()}</td>
      </tr>
    </#list>
    </tbody>
  </table>
</main>
</body>
</html>