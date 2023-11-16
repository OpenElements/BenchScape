/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          navy: '#020144',
          green:  '#5CBA9E',
          gray: '#F8F9FA',
          purple: '#9492F64F',
        },
        highlight: {
          blue: '#2926FC',
        }
      }
    },
  },
  plugins: [

  ],
}

