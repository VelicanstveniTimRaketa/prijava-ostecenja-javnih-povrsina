import { createContext } from "react";
import { GlobalState, Page } from "../utils/types";

export const STATE: GlobalState = {
  page: Page.MAIN,
  user: undefined,
};

export const StateContext = createContext<{
  global: GlobalState,
  setGlobal: React.Dispatch<React.SetStateAction<GlobalState>>
}>({
  global: STATE,
  setGlobal: (STATE) => STATE,
});