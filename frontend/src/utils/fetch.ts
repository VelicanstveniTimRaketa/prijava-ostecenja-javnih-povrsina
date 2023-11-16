import { RcFile } from "antd/es/upload";
import { BarebonesPrijava, GradskiUred, Prijava, PrijaveOptions, Response, TipOstecenja, UserLogin, UserRegiser } from "./types";

function requestGet<T>(path: string, params?: Record<string, string>, token?: string): Promise<Response<T>> {
  const headers = token ? { Authorization: "Bearer " + token } : undefined;
  let fullPath = path;
  if (params) fullPath += "?" + new URLSearchParams(params);
  
  return new Promise(res => {
    fetch(fullPath, { headers })
      .then((resp) => resp.bodyUsed ? resp.json() : Promise.resolve({}))
      .then(res)
      .catch((error) => res({ success: false, error }));
  });
}


function requestPost<T>(path: string, data: unknown, token?: string): Promise<Response<T>> {
  const headers: HeadersInit = {
    "Content-Type": "application/json",
  };
  if (token) headers.Authorization = "Bearer " + token;

  return new Promise(res => {
    fetch(path, { method: "POST", body: JSON.stringify(data), headers })
      .then((resp) => resp.bodyUsed ? resp.json() : Promise.resolve({}))
      .then((r) => res(r))
      .catch((error) => res({ success: false, error }));
  });
}

export function getPrijava(id: number): Promise<Response<Prijava>> {
    return requestGet("/api/prijave/" + id);
}

export function getPrijave(
  options?: PrijaveOptions
): Promise<Response<Prijava[]>> {
  return new Promise((res) => {
    fetch("/api/prijave?" + new URLSearchParams(options))
      .then((resp) => resp.json())
      .then((r) => {
        r.data = r.data.map((val: Prijava) => ({
          ...val,
          prvoVrijemePrijave: new Date(val.prvoVrijemePrijave),
          vrijemeOtklona: val.vrijemeOtklona && new Date(val.vrijemeOtklona),
        }));
        res(r);
      })
      .catch((error) => res({ success: false, error }));
  });
}
export function addPrijava(
  prijava: BarebonesPrijava,
  images: RcFile[]
): Promise<Response<never>> {
  return new Promise((res) => {
    const data = new FormData();

    Object.entries(prijava).forEach((entry) =>
      data.append(entry[0], JSON.stringify(entry[1]))
    );
    images.forEach((im) => data.append("slike", im, im.name));

    fetch("/api/addPrijava", { method: "POST", body: data })
      .then((resp) => resp.json())
      .then((r) => console.log(r))
      .catch((error) => res({ success: false, error }));
  });
}

export function getUserPrijave(id: string): Promise<Response<Prijava[]>> {
  return requestGet("/api/prijave",  { id });
}

export function getOstecenja(): Promise<Response<TipOstecenja[]>> {
  return requestGet("/api/ostecenja");
}

export function getGradskiUredi(): Promise<Response<GradskiUred[]>> {
  return requestGet("/api/uredi");
}

export function login(data: UserLogin): Promise<Response<never>> {
  return requestPost("/api/login", data);
}

export function register(data: UserRegiser): Promise<Response<never>> {
  return requestPost("/api/register", data);
}
