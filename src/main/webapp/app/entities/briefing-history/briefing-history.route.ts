import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBriefingHistory, BriefingHistory } from 'app/shared/model/briefing-history.model';
import { BriefingHistoryService } from './briefing-history.service';
import { BriefingHistoryComponent } from './briefing-history.component';
import { BriefingHistoryDetailComponent } from './briefing-history-detail.component';
import { BriefingHistoryUpdateComponent } from './briefing-history-update.component';

@Injectable({ providedIn: 'root' })
export class BriefingHistoryResolve implements Resolve<IBriefingHistory> {
  constructor(private service: BriefingHistoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBriefingHistory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((briefingHistory: HttpResponse<BriefingHistory>) => {
          if (briefingHistory.body) {
            return of(briefingHistory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BriefingHistory());
  }
}

export const briefingHistoryRoute: Routes = [
  {
    path: '',
    component: BriefingHistoryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BriefingHistories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BriefingHistoryDetailComponent,
    resolve: {
      briefingHistory: BriefingHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BriefingHistories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BriefingHistoryUpdateComponent,
    resolve: {
      briefingHistory: BriefingHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BriefingHistories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BriefingHistoryUpdateComponent,
    resolve: {
      briefingHistory: BriefingHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BriefingHistories'
    },
    canActivate: [UserRouteAccessService]
  }
];
