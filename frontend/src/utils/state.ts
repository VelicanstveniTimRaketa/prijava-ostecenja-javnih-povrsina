import { createContext } from "react";
import { Page } from "../utils/types";

export const STATE = {
  page: Page.MAIN,
};

export const StateContext = createContext<{
  global: { page: Page; },
  setGlobal: React.Dispatch<React.SetStateAction<{ page: Page }>>
}>({
  global: STATE,
  setGlobal: (STATE) => STATE,
});