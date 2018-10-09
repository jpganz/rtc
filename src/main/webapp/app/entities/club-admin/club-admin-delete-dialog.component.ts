import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClub_admin } from 'app/shared/model/club-admin.model';
import { Club_adminService } from './club-admin.service';

@Component({
    selector: 'jhi-club-admin-delete-dialog',
    templateUrl: './club-admin-delete-dialog.component.html'
})
export class Club_adminDeleteDialogComponent {
    club_admin: IClub_admin;

    constructor(private club_adminService: Club_adminService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.club_adminService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'club_adminListModification',
                content: 'Deleted an club_admin'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-club-admin-delete-popup',
    template: ''
})
export class Club_adminDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ club_admin }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(Club_adminDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.club_admin = club_admin;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
