import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Route } from '../model/route';
import { Nodes } from '../model/nodes';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class RouteServiceService {

  private shortestPathUrl: string;

  constructor(private http: HttpClient) {
    this.shortestPathUrl = 'http://localhost:8080/path/shortest?';
  }

  public findShortestPath(route: Route): Observable<string[]> {
    return this.http.get<string[]>(this.shortestPathUrl + "source=" + route.source + "&destination=" + route.destination);
  }

}
