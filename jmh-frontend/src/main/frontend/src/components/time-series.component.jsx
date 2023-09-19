import React from "react";
import {useTimeSeries} from "../hooks/hooks";
import {useParams} from "react-router-dom";

const TimeSeriesComponent = ({type}) => {
  const {id} = useParams();
  const {data} = useTimeSeries(id);

  console.log(data, "getting data");

  return (
      <React.Fragment>
        {/* TODO: Return a loading state once the data is still loading */}
        {data && <div dangerouslySetInnerHTML={{__html: data}}></div>}
      </React.Fragment>
  );
};

export default TimeSeriesComponent;
