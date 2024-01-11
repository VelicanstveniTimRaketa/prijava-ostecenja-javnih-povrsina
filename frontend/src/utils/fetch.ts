import { RcFile } from "antd/es/upload";
import { AddPrijavaResponse, BarebonesPrijava, GradskiUred, LoginData, Prijava, PrijaveOptions, Response, TipOstecenja, User, UserLogin, UserRegiser } from "./types";

export class Fetcher {
  static token?: string;

  static setToken(token?: string) {
    this.token = token;
  }

  static request<T>(method: "GET" | "PATCH" | "DELETE", path: string, params?: Record<string, string>): Promise<Response<T>> {
    const headers = this.token ? { Authorization: "Bearer " + this.token } : undefined;
    let fullPath = path;
    if (params) fullPath += "?" + new URLSearchParams(params);

    return new Promise(res => {
      fetch(fullPath, { method, headers })
        .then(resp => resp.json())
        .then(res)
        .catch((error) => res({ success: false, errors: [error.toString()] }));
    });
  }

  static get<T>(path: string, params?: Record<string, string>): Promise<Response<T>> {
    return this.request<T>("GET", path, params);
  }

  static patch<T>(path: string, params?: Record<string, string>): Promise<Response<T>> {
    return this.request<T>("PATCH", path, params);
  }

  static delete<T>(path: string, params?: Record<string, string>): Promise<Response<T>> {
    return this.request<T>("DELETE", path, params);
  }

  static post<T>(path: string, data: FormData | unknown): Promise<Response<T>> {
    const headers: HeadersInit = {};
    if (this.token) headers.Authorization = "Bearer " + this.token;
    if (!(data instanceof FormData)) headers["Content-Type"] = "application/json";

    const body = data instanceof FormData ? data : JSON.stringify(data);

    return new Promise(res => {
      fetch(path, { method: "POST", body, headers })
        .then(resp => resp.json())
        .then(res)
        .catch((error) => res({ success: false, errors: [error.toString()] }));
    });
  }
}

function fixPrijava(p: Prijava) {
  p.prvoVrijemePrijave = new Date(p.prvoVrijemePrijave);
  p.vrijemeOtklona = p.vrijemeOtklona && new Date(p.vrijemeOtklona);
}

// GET REQUESTS

export async function getPrijava(id: number): Promise<Response<Prijava>> {
  const r = await Fetcher.get<Prijava>("/api/prijave/" + id);
  r.data && fixPrijava(r.data);
  return r;
}

export async function getPrijave(options?: PrijaveOptions): Promise<Response<Prijava[]>> {
  const r = await Fetcher.get<Prijava[]>("/api/prijave?" + new URLSearchParams(options));
  r.data?.forEach(fixPrijava);
  return r;
}

export async function getUserPrijave(id: string): Promise<Response<Prijava[]>> {
  const r = await Fetcher.get<Prijava[]>("/api/prijave", { id });
  r.data?.forEach(fixPrijava);
  return r;
}

export function getOstecenja(): Promise<Response<TipOstecenja[]>> {
  return Fetcher.get("/api/ostecenja");
}

export function getGradskiUredi(): Promise<Response<GradskiUred[]>> {
  return Fetcher.get("/api/uredi");
}

export function getNeaktivniGradskiUredi(): Promise<Response<GradskiUred[]>> {
  return Fetcher.get("/api/urediNeaktivni");
}


export function getAllUsers(): Promise<Response<User[]>> {
  return Fetcher.get("/api/korisnici");
}

// POST REQUESTS

export async function addPrijava(prijava: BarebonesPrijava, images: RcFile[]): Promise<Response<AddPrijavaResponse>> {
  const data = new FormData();

  Object.entries(prijava).forEach((entry) =>
    data.append(entry[0], typeof entry[1] === "string" ? entry[1] : JSON.stringify(entry[1]))
  );
  images.forEach((im) => data.append("slike", im, im.name));

  const r = await Fetcher.post<AddPrijavaResponse>("/api/addPrijava", data);
  if (r.data) {
    fixPrijava(r.data?.newReport);
    r.data.nearbyReports.forEach(fixPrijava);
  }
  return r;
}

export function deleteUser(id: number): Promise<Response<unknown>> {
  return Fetcher.post("/api/makeChild", { id });
}

export function login(data: UserLogin): Promise<Response<LoginData>> {
  return Fetcher.post("/api/login", data);
}

export function register(data: UserRegiser): Promise<Response<LoginData>> {
  return Fetcher.post("/api/register", data);
}

export function addGradskiUred(data: unknown): Promise<Response<LoginData>> {
  return Fetcher.post("/api/addGradskiUred", data);
}


// PATCH REQUESTS

export function connectPrijave(idOneConnecting: number, idTo: number): Promise<Response<unknown>> {
  return Fetcher.patch("/api/makeChild", { child_id: idOneConnecting.toString(), parent_id: idTo.toString() });
}

export function potvrdiUred(id: number): Promise<Response<unknown>> {
  return Fetcher.patch("/api/potvrdiUred", { id: id.toString() });
}

export function potvrdiZahtjevUUred(id: number): Promise<Response<unknown>> {
  return Fetcher.patch("/api/potvrdaZahtjeva", { id: id.toString() });
}

export function odbijZahtjevUUred(id: number): Promise<Response<unknown>> {
  return Fetcher.patch("/api/odbijanjeZahtjeva", { id: id.toString() });
}

export function udiUUred(id: number): Promise<Response<unknown>> {
  return Fetcher.patch("/api/zahtjevZaUlazak", { id: id.toString() });
}

export function dovrsiPrijavu(id: number): Promise<Response<unknown>> {
  return Fetcher.patch("/api/dovrsiPrijavu", { id: id.toString() });
}

// DELETE REQUESTS

export function odbijUred(id: number): Promise<Response<unknown>> {
  return Fetcher.delete("/api/odbijUred", { id: id.toString() });
}
