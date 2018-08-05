import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Competitie } from 'app/shared/model/competitie.model';
import { CompetitieService } from './competitie.service';
import { CompetitieComponent } from './competitie.component';
import { CompetitieDetailComponent } from './competitie-detail.component';
import { CompetitieUpdateComponent } from './competitie-update.component';
import { CompetitieDeletePopupComponent } from './competitie-delete-dialog.component';
import { ICompetitie } from 'app/shared/model/competitie.model';

@Injectable({ providedIn: 'root' })
export class CompetitieResolve implements Resolve<ICompetitie> {
    constructor(private service: CompetitieService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((competitie: HttpResponse<Competitie>) => competitie.body));
        }
        return of(new Competitie());
    }
}

export const competitieRoute: Routes = [
    {
        path: 'competitie',
        component: CompetitieComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'bluepersApp.competitie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'competitie/:id/view',
        component: CompetitieDetailComponent,
        resolve: {
            competitie: CompetitieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bluepersApp.competitie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'competitie/new',
        component: CompetitieUpdateComponent,
        resolve: {
            competitie: CompetitieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bluepersApp.competitie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'competitie/:id/edit',
        component: CompetitieUpdateComponent,
        resolve: {
            competitie: CompetitieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bluepersApp.competitie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const competitiePopupRoute: Routes = [
    {
        path: 'competitie/:id/delete',
        component: CompetitieDeletePopupComponent,
        resolve: {
            competitie: CompetitieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bluepersApp.competitie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
