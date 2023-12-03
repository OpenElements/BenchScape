import react from 'react'

function Select(props) {
  return (
        <div className="w-auto">
          <label htmlFor="location" className="block text-xs text-gray-700">
            {props.label}
          </label>
          <select
            className="mt-2  block w-full rounded bg-indigo-950/5 border-0 py-1.5 pl-3 pr-10 text-gray-900 ring-1 ring-inset ring-transparent focus:ring-1 focus:ring-indigo-800 text-sm leading-6"
            defaultValue="Options"
            >
              <option>Options</option>
              <option value="11">Windows 11</option>
              <option value="12">Windows 12</option>
              <option value="mac">MAC OS</option>
          </select>
        </div>
  )
}
export default Select;