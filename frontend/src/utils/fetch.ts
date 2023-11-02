import { ResponsePrijave } from "./types";

export function getPrijave(options?: never): Promise<ResponsePrijave> {
    return new Promise((res, rej) => {
      fetch("/prijave?" + new URLSearchParams(options))
        .then(resp => resp.json())
        .then(r => res({
          ...r,
          prvoVrijemePrijave: new Date(r.prvoVrijemePrijave),
          vrijemeOtklona: r.vrijemeOtklona ? new Date(r.vrijemeOtklona) : undefined,
        }))
        .catch(error => rej({
          success: false,
          error
        }));
    });
}