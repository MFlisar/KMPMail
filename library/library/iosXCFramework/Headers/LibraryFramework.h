#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import <MessageUI/MessageUI.h>
#if __has_include(<UniformTypeIdentifiers/UniformTypeIdentifiers.h>)
#import <UniformTypeIdentifiers/UniformTypeIdentifiers.h>
#endif
#if __has_include(<MobileCoreServices/MobileCoreServices.h>)
#import <MobileCoreServices/MobileCoreServices.h>
#endif

FOUNDATION_EXPORT double LibraryFrameworkVersionNumber;
FOUNDATION_EXPORT const unsigned char LibraryFrameworkVersionString[];

// Swift API als ObjC Header verfügbar machen
#import <LibraryFramework/LibraryFramework-Swift.h>
