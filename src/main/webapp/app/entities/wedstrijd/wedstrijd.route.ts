import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Wedstrijd } from 'app/shared/model/wedstrijd.model';
import { WedstrijdService } from './wedstrijd.service';
import { WedstrijdComponent } from './wedstrijd.component';
import { WedstrijdDetailComponent } from './wedstrijd-detail.component';
import { WedstrijdUpdateComponent } from './wedstrijd-update.component';
import { WedstrijdDeletePopupComponent } from './wedstrijd-delete-dialog.component';
import { IWedstrijd } from 'app/shared/model/wedstrijd.model';

@Injectable({ providedIn: 'root' })
export class WedstrijdResolve implements Resolve<IWedstrijd> {
    constructor(private service: WedstrijdService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((wedstrijd: HttpResponse<Wedstrijd>) => wedstrijd.body));
        }
        return of(new Wedstrijd());
    }
}

export const wedstrijdRoute: Routes = [
    {
        path: 'wedstrijd',
        component: WedstrijdComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'bluepersApp.wedstrijd.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wedstrijd/:id/view',
        component: WedstrijdDetailComponent,
        resolve: {
            wedstrijd: WedstrijdResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bluepersApp.wedstrijd.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wedstrijd/new',
        component: WedstrijdUpdateComponent,
        resolve: {
            wedstrijd: WedstrijdResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bluepersApp.wedstrijd.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wedstrijd/:id/edit',
        component: WedstrijdUpdateComponent,
        resolve: {
            wedstrijd: WedstrijdResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bluepersApp.wedstrijd.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const wedstrijdPopupRoute: Routes = [
    {
        path: 'wedstrijd/:id/delete',
        component: WedstrijdDeletePopupComponent,
        resolve: {
            wedstrijd: WedstrijdResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bluepersApp.wedstrijd.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
