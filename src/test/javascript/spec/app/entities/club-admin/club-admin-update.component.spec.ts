/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RtcprojectTestModule } from '../../../test.module';
import { Club_adminUpdateComponent } from 'app/entities/club-admin/club-admin-update.component';
import { Club_adminService } from 'app/entities/club-admin/club-admin.service';
import { Club_admin } from 'app/shared/model/club-admin.model';

describe('Component Tests', () => {
    describe('Club_admin Management Update Component', () => {
        let comp: Club_adminUpdateComponent;
        let fixture: ComponentFixture<Club_adminUpdateComponent>;
        let service: Club_adminService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtcprojectTestModule],
                declarations: [Club_adminUpdateComponent]
            })
                .overrideTemplate(Club_adminUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(Club_adminUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Club_adminService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Club_admin(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.club_admin = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Club_admin();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.club_admin = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
