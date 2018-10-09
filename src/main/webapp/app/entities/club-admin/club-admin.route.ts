import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Club_admin } from 'app/shared/model/club-admin.model';
import { Club_adminService } from './club-admin.service';
import { Club_adminComponent } from './club-admin.component';
import { Club_adminDetailComponent } from './club-admin-detail.component';
import { Club_adminUpdateComponent } from './club-admin-update.component';
import { Club_adminDeletePopupComponent } from './club-admin-delete-dialog.component';
import { IClub_admin } from 'app/shared/model/club-admin.model';

@Injectable({ providedIn: 'root' })
export class Club_adminResolve implements Resolve<IClub_admin> {
    constructor(private service: Club_adminService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((club_admin: HttpResponse<Club_admin>) => club_admin.body));
        }
        return of(new Club_admin());
    }
}

export const club_adminRoute: Routes = [
    {
        path: 'club-admin',
        component: Club_adminComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'rtcprojectApp.club_admin.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'club-admin/:id/view',
        component: Club_adminDetailComponent,
        resolve: {
            club_admin: Club_adminResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rtcprojectApp.club_admin.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'club-admin/new',
        component: Club_adminUpdateComponent,
        resolve: {
            club_admin: Club_adminResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rtcprojectApp.club_admin.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'club-admin/:id/edit',
        component: Club_adminUpdateComponent,
        resolve: {
            club_admin: Club_adminResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rtcprojectApp.club_admin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const club_adminPopupRoute: Routes = [
    {
        path: 'club-admin/:id/delete',
        component: Club_adminDeletePopupComponent,
        resolve: {
            club_admin: Club_adminResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rtcprojectApp.club_admin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
