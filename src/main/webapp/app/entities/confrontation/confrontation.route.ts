import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Confrontation } from 'app/shared/model/confrontation.model';
import { ConfrontationService } from './confrontation.service';
import { ConfrontationComponent } from './confrontation.component';
import { ConfrontationDetailComponent } from './confrontation-detail.component';
import { ConfrontationUpdateComponent } from './confrontation-update.component';
import { ConfrontationDeletePopupComponent } from './confrontation-delete-dialog.component';
import { IConfrontation } from 'app/shared/model/confrontation.model';

@Injectable({ providedIn: 'root' })
export class ConfrontationResolve implements Resolve<IConfrontation> {
    constructor(private service: ConfrontationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IConfrontation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Confrontation>) => response.ok),
                map((confrontation: HttpResponse<Confrontation>) => confrontation.body)
            );
        }
        return of(new Confrontation());
    }
}

export const confrontationRoute: Routes = [
    {
        path: '',
        component: ConfrontationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Confrontations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ConfrontationDetailComponent,
        resolve: {
            confrontation: ConfrontationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Confrontations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ConfrontationUpdateComponent,
        resolve: {
            confrontation: ConfrontationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Confrontations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ConfrontationUpdateComponent,
        resolve: {
            confrontation: ConfrontationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Confrontations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const confrontationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ConfrontationDeletePopupComponent,
        resolve: {
            confrontation: ConfrontationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Confrontations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
