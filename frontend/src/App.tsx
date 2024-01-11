import "./App.css";
import { useEffect, useState } from "react";
import { STATE, StateContext } from "./utils/state";
import { Route, Routes } from "react-router";
import { BrowserRouter } from "react-router-dom";
import { Fetcher } from "./utils/fetch";
import Main from "./pages/Main";
import Login from "./pages/Login";
import Register from "./pages/Register";

function App() {
  const [global, setGlobal] = useState(STATE);

  useEffect(() => {
    if (global.user) {
      localStorage.setItem("token", global.user?.token);
    }
    Fetcher.setToken(global.user?.token);
  }, [global.user]);

  return (
    <BrowserRouter>
      <StateContext.Provider value={{ global, setGlobal }}>
        <Routes>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/register" element={<Register />}></Route>
          <Route path="*" element={<Main />}></Route>
        </Routes>
      </StateContext.Provider>
    </BrowserRouter>
  );
}

export default App;
