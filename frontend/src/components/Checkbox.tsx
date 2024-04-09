import { InputHTMLAttributes } from "react";

interface CheckboxProps extends InputHTMLAttributes<HTMLInputElement> {
  label: string;
}

function Checkbox({ label, ...restProps }: CheckboxProps) {
  return (
    <div className="flex items-center">
      <input type="checkbox" {...restProps} />
      <label htmlFor="ShowMin" className="ml-2 text-xs text-gray-500">
        {label}
      </label>
    </div>
  );
}

export default Checkbox;
