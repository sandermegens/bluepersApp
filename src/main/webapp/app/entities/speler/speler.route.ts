import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Speler } from 'app/shared/model/speler.model';
import { SpelerService } from './speler.service';
import { SpelerComponent } from './speler.component';
import { SpelerDetailComponent } from './speler-detail.component';
import { SpelerUpdateComponent } from './speler-update.component';
import { SpelerDeletePopupComponent } from './speler-delete-dialog.component';
import { ISpeler } from 'app/shared/model/speler.model';

@Injectable({ providedIn: 'root' })
export class SpelerResolve implements Resolve<ISpeler> {
    constructor(private service: SpelerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((speler: HttpResponse<Speler>) => speler.body));
        }
        return of(new Speler());
    }
}

export const spelerRoute: Routes = [
    {
        path: 'speler',
        component: SpelerComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'bluepersApp.speler.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'speler/:id/view',
        component: SpelerDetailComponent,
        resolve: {
            speler: SpelerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bluepersApp.speler.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'speler/new',
        component: SpelerUpdateComponent,
        resolve: {
            speler: SpelerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bluepersApp.speler.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'speler/:id/edit',
        component: SpelerUpdateComponent,
        resolve: {
            speler: SpelerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bluepersApp.speler.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const spelerPopupRoute: Routes = [
    {
        path: 'speler/:id/delete',
        component: SpelerDeletePopupComponent,
        resolve: {
            speler: SpelerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bluepersApp.speler.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
