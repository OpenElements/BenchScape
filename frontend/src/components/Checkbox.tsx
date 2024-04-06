import React from "react";

function Checkbox({ name, label, onChange, value, ...restProps }) {
  return (
    <div className="flex items-center">
      <input
        name={name}
        type="checkbox"
        value={value}
        onChange={onChange}
        {...restProps}
      />
      <label htmlFor="ShowMin" className="ml-2 text-xs text-gray-500">
        {label}
      </label>
    </div>
  );
}

export default Checkbox;
