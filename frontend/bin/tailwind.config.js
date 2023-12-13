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
        dark: '#2D2C28',
        highlight: {
          blue: '#2926FC',
        },
        azure: '#E1F0EC',
        winkle: '#D4D4FE',
        'alice-blue': '#DFF1FD',
      }
    },
  },
  plugins: [
    require('@tailwindcss/forms'),
  ],
}

