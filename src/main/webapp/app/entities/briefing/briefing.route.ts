import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBriefing, Briefing } from 'app/shared/model/briefing.model';
import { BriefingService } from './briefing.service';
import { BriefingComponent } from './briefing.component';
import { BriefingDetailComponent } from './briefing-detail.component';
import { BriefingUpdateComponent } from './briefing-update.component';

@Injectable({ providedIn: 'root' })
export class BriefingResolve implements Resolve<IBriefing> {
  constructor(private service: BriefingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBriefing> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((briefing: HttpResponse<Briefing>) => {
          if (briefing.body) {
            return of(briefing.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Briefing());
  }
}

export const briefingRoute: Routes = [
  {
    path: '',
    component: BriefingComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Briefings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BriefingDetailComponent,
    resolve: {
      briefing: BriefingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Briefings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BriefingUpdateComponent,
    resolve: {
      briefing: BriefingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Briefings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BriefingUpdateComponent,
    resolve: {
      briefing: BriefingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Briefings'
    },
    canActivate: [UserRouteAccessService]
  }
];
