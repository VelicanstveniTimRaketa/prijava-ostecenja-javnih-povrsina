import "./App.css";
import { useEffect, useState } from "react";
import { STATE, StateContext } from "./utils/state";
import { Route, Routes } from "react-router";
import { BrowserRouter } from "react-router-dom";
import { Fetcher, getUserFromToken } from "./utils/fetch";
import Main from "./pages/Main";
import Login from "./pages/Login";
import Register from "./pages/Register";

function App() {
  const [global, setGlobal] = useState(STATE);

  useEffect(() => {
    if (global.user) {
      localStorage.setItem("token", global.user?.token);
    }
    const savedToken = localStorage.getItem("token");
    if (!global.user && savedToken != null) {
      Fetcher.setToken(savedToken);
      getUserFromToken().then(u => setGlobal({...global, user: {...u, token: savedToken}}));
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
