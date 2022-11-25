import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import BenchmarkDiagramm from './BenchmarkDiagramm';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <BenchmarkDiagramm showMinMax showValue
                           benchmark="com.openelements.benchmark.SampleBenchmark.doIt"></BenchmarkDiagramm>
    </React.StrictMode>
);
