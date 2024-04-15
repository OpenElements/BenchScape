interface SelectProps<T> {
  label?: string;
  value: string;
  options: Array<T>;
  name?: string;
  onChange: (e) => void;
  labelExtractor: (option: T) => string;
  valueExtractor: (option: T) => string;
  disabled?: boolean;
}

function Select<T>({
  label,
  value,
  options,
  onChange,
  name,
  labelExtractor,
  valueExtractor,
  disabled,
  ...restProps
}: SelectProps<T>) {
  return (
    <div className="w-auto">
      <label
        role="label"
        htmlFor="label"
        className="block text-xs text-gray-700"
      >
        {label}
      </label>
      <select
        role="select"
        name={name}
        value={value}
        onChange={onChange}
        disabled={disabled}
        className={`mt-2  block w-full rounded bg-indigo-950/5 border-0 py-1.5 pl-3 pr-10 text-gray-900 ring-1 ring-inset ring-transparent focus:ring-1 focus:ring-indigo-800 text-sm leading-6 ${
          disabled && "cursor-not-allowed"
        }`}
        defaultValue=""
        {...restProps}
      >
        <option value={""}>{disabled ? "--" : "all"}</option>
        {options?.map((option, index) => (
          <option key={index} value={valueExtractor(option)}>
            {labelExtractor(option)}
          </option>
        ))}
      </select>
    </div>
  );
}
export default Select;
