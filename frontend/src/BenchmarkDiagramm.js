import {
    CategoryScale,
    Chart as ChartJS,
    Legend,
    LinearScale,
    LineElement,
    PointElement,
    TimeScale,
    Title,
    Tooltip,
} from 'chart.js';
import 'chartjs-adapter-luxon';

import {Line} from 'react-chartjs-2';

import {useEffect, useState} from 'react'

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
    TimeScale
);

function BenchmarkDiagramm(props) {

    const [chartData, setChartData] = useState({datasets: []});

    const [options, setOptions] = useState({
        plugins: {
            legend: {
                display: false
            },
            title: {
                display: false,
            }
        }
    });


    let loadData = () => {
        let benchmarkName = props.benchmark;
        fetch("http://localhost:8080/timeseries?benchmark=" + benchmarkName).then(function (response) {
            return response.json();
        }).then(function (data) {
            console.log(data);

            var dataset = {
                data: [],
                fill: false,
                tension: 0, //Damit die Übergänge zwischen Punkten eckig sind
                borderColor: 'blue',
                pointStyle: 'circle',
                pointRadius: 0,
                borderWidth: 2
            };
            var lowErrorDataset = {
                data: [],
                fill: false,
                tension: 0, //Damit die Übergänge zwischen Punkten eckig sind
                borderColor: 'red',
                pointStyle: 'circle',
                pointRadius: 0,
                borderWidth: 2
            };
            var highErrorDataset = {
                data: [],
                fill: false,
                tension: 0, //Damit die Übergänge zwischen Punkten eckig sind
                borderColor: 'red',
                pointStyle: 'circle',
                pointRadius: 0,
                borderWidth: 2
            };
            var minDataset = {
                data: [],
                fill: false,
                tension: 0, //Damit die Übergänge zwischen Punkten eckig sind
                borderColor: 'orange',
                pointStyle: 'circle',
                pointRadius: 0,
                borderWidth: 2
            };
            var maxDataset = {
                data: [],
                fill: false,
                tension: 0, //Damit die Übergänge zwischen Punkten eckig sind
                borderColor: 'orange',
                pointStyle: 'circle',
                pointRadius: 0,
                borderWidth: 2
            };
            data.forEach(element => {
                var dataEntry = {
                    x: element.timestamp, y: element.value
                }
                dataset.data.push(dataEntry);
                var lowErrordataEntry = {
                    x: element.timestamp, y: element.value - element.error
                }
                lowErrorDataset.data.push(lowErrordataEntry);
                var highErrordataEntry = {
                    x: element.timestamp, y: element.value + element.error
                }
                highErrorDataset.data.push(highErrordataEntry);
                var minDataEntry = {
                    x: element.timestamp, y: element.min
                }
                minDataset.data.push(minDataEntry);
                var maxDataEntry = {
                    x: element.timestamp, y: element.max
                }
                maxDataset.data.push(maxDataEntry);
            });
            let newDatasets = [];
            if (props.showMinMax) {
                newDatasets.push(minDataset, maxDataset);
            }
            if (props.showValue) {
                newDatasets.push(dataset);
            }
            setChartData({datasets: newDatasets});

            setOptions({
                plugins: {
                    legend: {
                        display: false
                    },
                    title: {
                        display: true,
                        text: "Benchmark: " + benchmarkName,
                    },
                    tooltip: {
                        enabled: false
                    }
                },
                responsive: true,
                scales: {
                    x: {
                        type: 'time',
                        title: {
                            display: false
                        }
                    },
                    y: {
                        title: {
                            display: true,
                            text: 'ops/ms'
                        }
                    }
                }
            })
        }).catch(function (e) {
            console.log("Booo", e);
        });
    };

    useEffect(() => {
        loadData();
    }, []);

    return (
        <Line options={options} data={chartData}/>
    );
}

export default BenchmarkDiagramm;