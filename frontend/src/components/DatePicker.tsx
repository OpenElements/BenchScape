import React from "react";
import Flatpickr from "react-flatpickr";

function Datepicker({ align, label, onChange, value, ...restProps }) {
  const options = {
    mode: "single",
    static: true,
    monthSelectorType: "static",
    dateFormat: "M j, Y",
    prevArrow:
      '<svg class="fill-current" width="7" height="11" viewBox="0 0 7 11"><path d="M5.4 10.8l1.4-1.4-4-4 4-4L5.4 0 0 5.4z" /></svg>',
    nextArrow:
      '<svg class="fill-current" width="7" height="11" viewBox="0 0 7 11"><path d="M1.4 10.8L0 9.4l4-4-4-4L1.4 0l5.4 5.4z" /></svg>',
    onChange: (selectedDates, dateStr, instance) => {
      const dateObject = new Date(selectedDates[0]);

      const options = { month: "short", day: "numeric", year: "numeric" };
      const formattedDate = dateObject.toLocaleString("en-US", options);
      instance.element.value = formattedDate;
    },
  };

  return (
    <div className="relative">
      <label htmlFor="location" className="block text-xs text-gray-700 mb-2">
        {label}
      </label>
      <Flatpickr
        className="form-input pl-9 text-slate-500 hover:text-slate-900 font-medium w-[15.5rem] text-xs rounded border-slate-300"
        options={options}
        value={value}
        onChange={(date) => onChange(new Date(date).toISOString())}
        {...restProps}
      />
      <div className="absolute inset-0 right-auto flex items-center pointer-events-none top-5">
        <svg
          className="w-4 h-4 fill-current text-slate-500 dark:text-slate-400 ml-3"
          viewBox="0 0 16 16"
        >
          <path d="M15 2h-2V0h-2v2H9V0H7v2H5V0H3v2H1a1 1 0 00-1 1v12a1 1 0 001 1h14a1 1 0 001-1V3a1 1 0 00-1-1zm-1 12H2V6h12v8z" />
        </svg>
      </div>
    </div>
  );
}

export default Datepicker;
