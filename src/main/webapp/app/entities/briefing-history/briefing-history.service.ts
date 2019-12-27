import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBriefingHistory } from 'app/shared/model/briefing-history.model';

type EntityResponseType = HttpResponse<IBriefingHistory>;
type EntityArrayResponseType = HttpResponse<IBriefingHistory[]>;

@Injectable({ providedIn: 'root' })
export class BriefingHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/briefing-histories';

  constructor(protected http: HttpClient) {}

  create(briefingHistory: IBriefingHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(briefingHistory);
    return this.http
      .post<IBriefingHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(briefingHistory: IBriefingHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(briefingHistory);
    return this.http
      .put<IBriefingHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBriefingHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBriefingHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(briefingHistory: IBriefingHistory): IBriefingHistory {
    const copy: IBriefingHistory = Object.assign({}, briefingHistory, {
      startDate: briefingHistory.startDate && briefingHistory.startDate.isValid() ? briefingHistory.startDate.toJSON() : undefined,
      endDate: briefingHistory.endDate && briefingHistory.endDate.isValid() ? briefingHistory.endDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? moment(res.body.endDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((briefingHistory: IBriefingHistory) => {
        briefingHistory.startDate = briefingHistory.startDate ? moment(briefingHistory.startDate) : undefined;
        briefingHistory.endDate = briefingHistory.endDate ? moment(briefingHistory.endDate) : undefined;
      });
    }
    return res;
  }
}
