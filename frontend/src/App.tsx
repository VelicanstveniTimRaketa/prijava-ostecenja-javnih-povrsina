import "./App.css";
import { useEffect, useState } from "react";
import { STATE, StateContext } from "./utils/state";
import { Route, Routes } from "react-router";
import { BrowserRouter } from "react-router-dom";
import { Fetcher, getUserFromToken, refreshToken } from "./utils/fetch";
import Main from "./pages/Main";
import Login from "./pages/Login";
import Register from "./pages/Register";

function App() {
  const [global, setGlobal] = useState(STATE);

  useEffect(() => {
    if (global.user) {
      localStorage.setItem("token", global.user?.token);
      localStorage.setItem("refreshToken", global.user?.refreshToken);
    }
    const savedToken = localStorage.getItem("token");
    const savedRefreshToken = localStorage.getItem("token");

    if (!global.user && savedToken !== null && savedRefreshToken !== null) {
      Fetcher.setToken(savedToken);
      getUserFromToken().then(u => {
        if (!u.success) {
          //Fetcher.setToken(undefined);
          console.log("refreshing token");
          refreshToken().then(v => {
            console.log(v);
          });
        }
        u.success && u.data && setGlobal({ ...global, user: { ...u.data, token: savedToken, refreshToken: savedRefreshToken } });
      });
    }
    Fetcher.setToken(global.user?.token);
    // eslint-disable-next-line react-hooks/exhaustive-deps
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
