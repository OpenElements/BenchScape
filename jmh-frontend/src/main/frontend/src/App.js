import React, { useEffect, useState } from "react";
import logo from './assets/logo.svg';
import user from './assets/images/user.jpg';
import { ArrowsClockwise, Star, ChartLine, Bell, Gear, House, CaretDown, SignOut } from "@phosphor-icons/react";

function App() {
  const [data, setData] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    // Define the API URL here using the environment variable
    const apiUrl = process.env.REACT_APP_API_URL;

    // Fetch data from the "/api/benchmark" endpoint
    fetch(`${apiUrl}/api/benchmark`)
      .then((response) => response.json())
      .then((data) => {
        setData(data);
        setIsLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
        setIsLoading(false);
      });
  }, []);

  return (
    <div className="bg-primary-gray">
      <div>
        <div class="relative z-50 hidden" role="dialog" aria-modal="true">

          <div class="fixed inset-0 bg-primary-navy/80"></div>

          <div class="fixed inset-0 flex">

            <div class="relative mr-16 flex w-full max-w-xs flex-1">

              <div class="absolute left-full top-0 flex w-16 justify-center pt-5">
                <button type="button" class="-m-2.5 p-2.5">
                  <span class="sr-only">Close sidebar</span>
                  <svg class="h-6 w-6 text-white" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12" />
                  </svg>
                </button>
              </div>

              <div class="flex grow flex-col gap-y-5 overflow-y-auto bg-gray-900 px-6 pb-4 ring-1 ring-white/10">
                <div class="flex h-16 shrink-0 items-center">
                  <p>image</p>
                </div>
                <nav class="flex flex-1 flex-col">
                  <ul role="list" class="flex flex-1 flex-col gap-y-7">
                    <li>
                      <ul role="list" class="-mx-2 space-y-1">
                        <li>

                          <a href="#" class="bg-gray-800 text-white group flex gap-x-3 rounded-md p-2 text-sm leading-6 font-semibold">
                            <svg class="h-6 w-6 shrink-0" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true">
                              <path stroke-linecap="round" stroke-linejoin="round" d="M2.25 12l8.954-8.955c.44-.439 1.152-.439 1.591 0L21.75 12M4.5 9.75v10.125c0 .621.504 1.125 1.125 1.125H9.75v-4.875c0-.621.504-1.125 1.125-1.125h2.25c.621 0 1.125.504 1.125 1.125V21h4.125c.621 0 1.125-.504 1.125-1.125V9.75M8.25 21h8.25" />
                            </svg>
                            Dashboard
                          </a>
                        </li>
                        <li>
                          <a href="#" class="text-gray-400 hover:text-white hover:bg-gray-800 group flex gap-x-3 rounded-md p-2 text-sm leading-6 font-semibold">
                            <svg class="h-6 w-6 shrink-0" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true">
                              <path stroke-linecap="round" stroke-linejoin="round" d="M15 19.128a9.38 9.38 0 002.625.372 9.337 9.337 0 004.121-.952 4.125 4.125 0 00-7.533-2.493M15 19.128v-.003c0-1.113-.285-2.16-.786-3.07M15 19.128v.106A12.318 12.318 0 018.624 21c-2.331 0-4.512-.645-6.374-1.766l-.001-.109a6.375 6.375 0 0111.964-3.07M12 6.375a3.375 3.375 0 11-6.75 0 3.375 3.375 0 016.75 0zm8.25 2.25a2.625 2.625 0 11-5.25 0 2.625 2.625 0 015.25 0z" />
                            </svg>
                            Team
                          </a>
                        </li>
                        <li>
                          <a href="#" class="text-gray-400 hover:text-white hover:bg-gray-800 group flex gap-x-3 rounded-md p-2 text-sm leading-6 font-semibold">
                            <svg class="h-6 w-6 shrink-0" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true">
                              <path stroke-linecap="round" stroke-linejoin="round" d="M2.25 12.75V12A2.25 2.25 0 014.5 9.75h15A2.25 2.25 0 0121.75 12v.75m-8.69-6.44l-2.12-2.12a1.5 1.5 0 00-1.061-.44H4.5A2.25 2.25 0 002.25 6v12a2.25 2.25 0 002.25 2.25h15A2.25 2.25 0 0021.75 18V9a2.25 2.25 0 00-2.25-2.25h-5.379a1.5 1.5 0 01-1.06-.44z" />
                            </svg>
                            Projects
                          </a>
                        </li>
                        <li>
                          <a href="#" class="text-gray-400 hover:text-white hover:bg-gray-800 group flex gap-x-3 rounded-md p-2 text-sm leading-6 font-semibold">
                            <svg class="h-6 w-6 shrink-0" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true">
                              <path stroke-linecap="round" stroke-linejoin="round" d="M6.75 3v2.25M17.25 3v2.25M3 18.75V7.5a2.25 2.25 0 012.25-2.25h13.5A2.25 2.25 0 0121 7.5v11.25m-18 0A2.25 2.25 0 005.25 21h13.5A2.25 2.25 0 0021 18.75m-18 0v-7.5A2.25 2.25 0 015.25 9h13.5A2.25 2.25 0 0121 11.25v7.5" />
                            </svg>
                            Calendar
                          </a>
                        </li>
                        <li>
                          <a href="#" class="text-gray-400 hover:text-white hover:bg-gray-800 group flex gap-x-3 rounded-md p-2 text-sm leading-6 font-semibold">
                            <svg class="h-6 w-6 shrink-0" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true">
                              <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 17.25v3.375c0 .621-.504 1.125-1.125 1.125h-9.75a1.125 1.125 0 01-1.125-1.125V7.875c0-.621.504-1.125 1.125-1.125H6.75a9.06 9.06 0 011.5.124m7.5 10.376h3.375c.621 0 1.125-.504 1.125-1.125V11.25c0-4.46-3.243-8.161-7.5-8.876a9.06 9.06 0 00-1.5-.124H9.375c-.621 0-1.125.504-1.125 1.125v3.5m7.5 10.375H9.375a1.125 1.125 0 01-1.125-1.125v-9.25m12 6.625v-1.875a3.375 3.375 0 00-3.375-3.375h-1.5a1.125 1.125 0 01-1.125-1.125v-1.5a3.375 3.375 0 00-3.375-3.375H9.75" />
                            </svg>
                            Documents
                          </a>
                        </li>
                        <li>
                          <a href="#" class="text-gray-400 hover:text-white hover:bg-gray-800 group flex gap-x-3 rounded-md p-2 text-sm leading-6 font-semibold">
                            <svg class="h-6 w-6 shrink-0" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true">
                              <path stroke-linecap="round" stroke-linejoin="round" d="M10.5 6a7.5 7.5 0 107.5 7.5h-7.5V6z" />
                              <path stroke-linecap="round" stroke-linejoin="round" d="M13.5 10.5H21A7.5 7.5 0 0013.5 3v7.5z" />
                            </svg>
                            Reports
                          </a>
                        </li>
                      </ul>
                    </li>
                    <li>
                      <div class="text-xs font-semibold leading-6 text-gray-400">Your teams</div>
                      <ul role="list" class="-mx-2 mt-2 space-y-1">
                        <li>

                          <a href="#" class="text-gray-400 hover:text-white hover:bg-gray-800 group flex gap-x-3 rounded-md p-2 text-sm leading-6 font-semibold">
                            <span class="flex h-6 w-6 shrink-0 items-center justify-center rounded-lg border border-gray-700 bg-gray-800 text-[0.625rem] font-medium text-gray-400 group-hover:text-white">H</span>
                            <span class="truncate">Heroicons</span>
                          </a>
                        </li>
                        <li>
                          <a href="#" class="text-gray-400 hover:text-white hover:bg-gray-800 group flex gap-x-3 rounded-md p-2 text-sm leading-6 font-semibold">
                            <span class="flex h-6 w-6 shrink-0 items-center justify-center rounded-lg border border-gray-700 bg-gray-800 text-[0.625rem] font-medium text-gray-400 group-hover:text-white">T</span>
                            <span class="truncate">Tailwind Labs</span>
                          </a>
                        </li>
                        <li>
                          <a href="#" class="text-gray-400 hover:text-white hover:bg-gray-800 group flex gap-x-3 rounded-md p-2 text-sm leading-6 font-semibold">
                            <span class="flex h-6 w-6 shrink-0 items-center justify-center rounded-lg border border-gray-700 bg-gray-800 text-[0.625rem] font-medium text-gray-400 group-hover:text-white">W</span>
                            <span class="truncate">Workcation</span>
                          </a>
                        </li>
                      </ul>
                    </li>
                    <li class="mt-auto">
                      <a href="#" class="group -mx-2 flex gap-x-3 rounded-md p-2 text-sm font-semibold leading-6 text-gray-400 hover:bg-gray-800 hover:text-white">
                        <svg class="h-6 w-6 shrink-0" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true">
                          <path stroke-linecap="round" stroke-linejoin="round" d="M9.594 3.94c.09-.542.56-.94 1.11-.94h2.593c.55 0 1.02.398 1.11.94l.213 1.281c.063.374.313.686.645.87.074.04.147.083.22.127.324.196.72.257 1.075.124l1.217-.456a1.125 1.125 0 011.37.49l1.296 2.247a1.125 1.125 0 01-.26 1.431l-1.003.827c-.293.24-.438.613-.431.992a6.759 6.759 0 010 .255c-.007.378.138.75.43.99l1.005.828c.424.35.534.954.26 1.43l-1.298 2.247a1.125 1.125 0 01-1.369.491l-1.217-.456c-.355-.133-.75-.072-1.076.124a6.57 6.57 0 01-.22.128c-.331.183-.581.495-.644.869l-.213 1.28c-.09.543-.56.941-1.11.941h-2.594c-.55 0-1.02-.398-1.11-.94l-.213-1.281c-.062-.374-.312-.686-.644-.87a6.52 6.52 0 01-.22-.127c-.325-.196-.72-.257-1.076-.124l-1.217.456a1.125 1.125 0 01-1.369-.49l-1.297-2.247a1.125 1.125 0 01.26-1.431l1.004-.827c.292-.24.437-.613.43-.992a6.932 6.932 0 010-.255c.007-.378-.138-.75-.43-.99l-1.004-.828a1.125 1.125 0 01-.26-1.43l1.297-2.247a1.125 1.125 0 011.37-.491l1.216.456c.356.133.751.072 1.076-.124.072-.044.146-.087.22-.128.332-.183.582-.495.644-.869l.214-1.281z" />
                          <path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                        </svg>
                        Settings
                      </a>
                    </li>
                  </ul>
                </nav>
              </div>
            </div>
          </div>
        </div>
        <div class="hidden lg:fixed lg:inset-y-0 lg:z-50 lg:flex lg:w-72 lg:flex-col">

          <div class="flex grow flex-col gap-y-5 overflow-y-auto bg-primary-navy pb-4 px-8">
            <div class="flex h-20 shrink-0 items-center justify-start">
              <img src={logo} className="h-10" />
            </div>
            <nav class="flex flex-1 flex-col">
              <ul role="list" class="flex flex-1 flex-col gap-y-7">
                <li>
                  <ul role="list" class="-mx-8 space-y-1">
                    <li>
                      <a href="" className="sidebar-nav-link">
                        <span class="flex items-center gap-3">
                          <ArrowsClockwise className="w-5 h-5 shrink-0" />
                          Regular Updates
                        </span>
                        <span className="sidebar-badge">3</span>
                      </a>
                    </li>
                    <li>
                      <a href="" className="sidebar-nav-link">
                        <span class="flex items-center gap-3">
                          <Star className="w-5 h-5 shrink-0" />
                          Favorites
                        </span>
                        <span className="sidebar-badge">12</span>
                      </a>
                    </li>
                    <li>
                      <a href="" className="sidebar-nav-link">
                        <span class="flex items-center gap-3">
                          <ChartLine className="w-5 h-5 shrink-0" />
                          Analytics
                        </span>
                      </a>
                    </li>
                    <li>
                      <a href="" className="sidebar-nav-link">
                        <span class="flex items-center gap-3">
                          <Bell className="w-5 h-5 shrink-0" />
                          Alerts
                        </span>
                      </a>
                    </li>
                  </ul>
                </li>

                <li class="mt-auto w-full">
                  <ul className='-mx-8'>
                    <li>
                      <a href="" className="sidebar-nav-link">
                        <span class="flex items-center gap-3">
                          <Gear className="w-5 h-5 shrink-0" />
                          Settings
                        </span>
                      </a>
                    </li>
                  </ul>

                </li>
              </ul>
            </nav>
          </div>
        </div>

        <div class="lg:pl-72 h-[calc(100vh-96px)]">
          <div class="sticky top-0 z-40 px-4 sm:px-6 xl:px-7 xl:py-5">

            <div className="flex items-center h-14 bg-white rounded-lg shadow-lg sm:gap-x-6 gap-x-4">
              <button type="button" class="-m-2.5 p-2.5 text-gray-700 lg:hidden">
                <span class="sr-only">Open sidebar</span>
                <svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
                </svg>
              </button>
              <div class="h-6 w-px bg-gray-900/10 lg:hidden" aria-hidden="true"></div>
              <div class="flex flex-1 gap-x-4 self-stretch lg:gap-x-6">
                <div className="flex-1 flex items-center px-8">
                  <div className="flex items-center gap-5 h-full">
                    <a href="#" className="w-12 h-full flex items-center justify-center border-b-2 border-highlight-blue">
                      <House className="w-5 h-5" weight="bold" />
                    </a>
                    <div className='w-0.5 h-4 bg-primary-navy/10'></div>
                    <div>
                      <button className="inline-flex items-center gap-2 text-primary-navy text-sm">
                        Menu Button
                        <CaretDown className="w-3 h-3 text-primary-navy" weight="fill" />
                      </button>
                    </div>
                    <div className='w-0.5 h-4 bg-primary-navy/10'></div>
                    <div>
                      <button className="inline-flex items-center gap-2 text-primary-navy text-sm">
                        Menu Button
                        <CaretDown className="w-3 h-3 text-primary-navy" weight="fill" />
                      </button>
                    </div>
                    <div className='w-0.5 h-4 bg-primary-navy/10'></div>
                    <div>
                      <button className="inline-flex items-center gap-2 text-primary-navy text-sm">
                        Menu Button
                        <CaretDown className="w-3 h-3 text-primary-navy" weight="fill" />
                      </button>
                    </div>
                    <div className='w-0.5 h-4 bg-primary-navy/10'></div>
                    <div>
                      <button className="inline-flex items-center gap-2 text-primary-navy text-sm">
                        Menu Button
                        <CaretDown className="w-3 h-3 text-primary-navy" weight="fill" />
                      </button>
                    </div>
                  </div>
                </div>
                <div class="flex items-center gap-5 px-8">
                  <div className="flex items-center gap-3">
                    <div className="w-8 h-8 border-2 border-highlight-blue rounded-full overflow-hidden">
                      <img src={user} alt="User" className="w-full h-full" />
                    </div>
                    <p className="text-primary-navy text-sm">Margo Doe</p>
                  </div>
                  <div className='w-0.5 h-4 bg-primary-navy/10'></div>
                  <button className="text-primary-navy hover:text-red-600 transition-colors ease-in-out duration-150">
                    <SignOut className="w-5 h-5" weight="bold" />
                  </button>
                </div>
              </div>
            </div>
          </div>
          <main class="w-full h-full px-4 sm:px-6 xl:px-7 xl:pb-5">
            <div className="bg-white rounded-lg w-full h-full shadow-lg">
              <div className="App">
              
                 
                  {isLoading ? (
                    "Loading..."
                  ) : (



                    <div class="px-4 sm:px-6 lg:px-8 py-7">
                      <div class="sm:flex sm:items-center">
                        <div class="sm:flex-auto">
                          <h1 class="text-xl font-semibold leading-6 text-primary-navy">BenchScape V1.1</h1>
                          <p class="mt-2 text-sm text-gray-700">
                          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus
                          </p>
                        </div>
                        <div class="mt-4 sm:ml-16 sm:mt-0 sm:flex-none">
                          <button type="button" class="block rounded-md bg-indigo-600 px-3 py-2 text-center text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Add user</button>
                        </div>
                      </div>
                      <div class="mt-8 flow-root">
                        <div class="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
                          <div class="inline-block min-w-full py-2 align-middle">
                            <table class="min-w-full divide-y divide-gray-300">
                              <thead>
                                <tr>
                                  <th scope="col" class="py-3.5 pl-4 pr-3 text-left text-sm font-semibold text-primary-navy sm:pl-6 lg:pl-8">ID</th>
                                  <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-primary-navy">Name</th>
                                  <th scope="col" class="relative py-3.5 pl-3 pr-4 sm:pr-6 lg:pr-8 text-sm font-semibold text-primary-navy">Unit</th>
                                </tr>
                              </thead>
                              <tbody class="divide-y divide-gray-200 bg-white">
                                {data.map((benchmark) => (
                                    <tr  key={benchmark.id}>
                                      <td class="whitespace-nowrap py-4 pl-4 pr-3 text-sm font-medium text-primary-navy sm:pl-6 lg:pl-8">
                                        {benchmark.id}
                                        </td>
                                      <td class="whitespace-nowrap px-3 py-4 text-sm text-gray-500">
                                        {benchmark.name}
                                        </td>
                                      <td class="relative whitespace-nowrap py-4 pl-3 pr-4 text-right text-sm font-medium sm:pr-6 lg:pr-8">
                                        {benchmark.unit}
                                      </td>
                                    </tr>
                                ))}
                              </tbody>
                            </table>
                          </div>
                        </div>
                      </div>
                    </div>
                  )}
              </div>
            </div>
          </main>
        </div>
      </div>

    </div>
  );
}

export default App;
