/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RtcprojectTestModule } from '../../../test.module';
import { Club_adminDeleteDialogComponent } from 'app/entities/club-admin/club-admin-delete-dialog.component';
import { Club_adminService } from 'app/entities/club-admin/club-admin.service';

describe('Component Tests', () => {
    describe('Club_admin Management Delete Component', () => {
        let comp: Club_adminDeleteDialogComponent;
        let fixture: ComponentFixture<Club_adminDeleteDialogComponent>;
        let service: Club_adminService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtcprojectTestModule],
                declarations: [Club_adminDeleteDialogComponent]
            })
                .overrideTemplate(Club_adminDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(Club_adminDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Club_adminService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
