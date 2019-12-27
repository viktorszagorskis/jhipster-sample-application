import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBriefing } from 'app/shared/model/briefing.model';

type EntityResponseType = HttpResponse<IBriefing>;
type EntityArrayResponseType = HttpResponse<IBriefing[]>;

@Injectable({ providedIn: 'root' })
export class BriefingService {
  public resourceUrl = SERVER_API_URL + 'api/briefings';

  constructor(protected http: HttpClient) {}

  create(briefing: IBriefing): Observable<EntityResponseType> {
    return this.http.post<IBriefing>(this.resourceUrl, briefing, { observe: 'response' });
  }

  update(briefing: IBriefing): Observable<EntityResponseType> {
    return this.http.put<IBriefing>(this.resourceUrl, briefing, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBriefing>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBriefing[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
